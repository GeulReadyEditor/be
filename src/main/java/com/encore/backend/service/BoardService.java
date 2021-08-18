package com.encore.backend.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.encore.backend.dto.BoardDTO;
import com.encore.backend.repository.board.BoardRepository;
import com.encore.backend.s3.S3Uploader;
import com.encore.backend.vo.BoardVO;
import com.encore.backend.vo.Comment;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
    private BoardRepository repo;
    private final S3Uploader s3Uploader;

    @Autowired
    public BoardService(BoardRepository repo, S3Uploader s3Uploader) {
        this.repo = repo;
        this.s3Uploader = s3Uploader;
    }

    public List<BoardVO> selectBoard(Map<String, Object> parameters) {
        String boardId = (String) parameters.get("boardId");
        Page<BoardVO> page = null;
        if (boardId != null) {
            page = repo.findById(boardId, PageRequest.of(0, 1));
        } else {
            int pageNumber = Integer.parseInt((String) parameters.get("pageNumber")) - 1;
            String title = (String) parameters.get("boardTitle");
            String nickName = (String) parameters.get("nickName");
            String tag = (String) parameters.get("tag");
            if (title != null) {
                page = repo.findByTitleLikeOrderByModDatetimeDesc(title, PageRequest.of(pageNumber, 5));
            } else if (nickName != null) {
                page = repo.findByNickNameLikeOrderByModDatetimeDesc(nickName, PageRequest.of(pageNumber, 5));
            } else if (tag != null) {
                page = repo.findByTagsLikeOrderByModDatetimeDesc(tag, PageRequest.of(pageNumber, 5));
            } else {
                page = repo.findAllByOrderByModDatetimeDesc(PageRequest.of(pageNumber, 5));
            }
        }
        return page.getContent();
    }

    public Long getBoardsCount(Map<String, Object> parameters) {
        Long result = -1L;
        String title = (String) parameters.get("boardTitle");
        String nickName = (String) parameters.get("nickName");
        String tag = (String) parameters.get("tag");
        if (title != null && nickName != null && tag != null) {
            result = repo.countByTitleLikeOrNickNameOrTagsIn(title, nickName, tag);
        } else if (title != null) {
            result = repo.countByTitleLike(title);
        } else if (nickName != null) {
            result = repo.countByNickNameLike(nickName);
        } else if (tag != null) {
            result = repo.countByTagsIn(tag);
        } else {
            result = repo.count();
        }
        return result;
    }

    public boolean upsertBoard(BoardDTO boardDTO, MultipartFile titleImageFile) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardVO board = mapper.map(boardDTO, BoardVO.class);
        board.setId(boardDTO.getBoardId());
        try {
            if (board.getId() == null) {
                BoardVO ret = repo.insert(board);
                board.setTitleImage(titleImageFile.getOriginalFilename().length() == 0 ? ""
                        : s3Uploader.upload(titleImageFile, "titleImages", ret.getId()));
                ret = repo.save(board);
                return true;
            } else {
                boolean check = repo.existsById(board.getId());
                if (!check)
                    return false;

                repo.save(board);
                return true;
            }
        } catch (Exception e) {
            log.info("error ", e);
            return false;
        }
    }

    public boolean deleteBoard(String boardId) {
        try {
            repo.deleteById(boardId);
            return repo.existsById(boardId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getComment(Map<String, Object> parameters) {
        int pageNumber = Integer.parseInt((String) parameters.get("pageNumber"));
        List<BoardVO> board = repo.findCommentsById((String) parameters.get("boardId"), (pageNumber - 1) * 10,
                pageNumber * 10);
        List<Comment> result = new ArrayList<Comment>();
        List<Comment> comments = board.get(0).getComments();
        if (comments != null)
            for (Comment c : board.get(0).getComments()) {
                result.add(c);
            }
        return result;
    }

    public boolean insertComment(Map<String, Object> parameters) {
        return repo.insertComment((String) parameters.get("boardId"),
                new Comment(new ObjectId().toString(), (String) parameters.get("userId"),
                        (String) parameters.get("nickName"), (String) parameters.get("comment"),
                        Calendar.getInstance().getTime()));
    }

    public boolean removeComment(Map<String, Object> parameters) {
        return repo.removeComment((String) parameters.get("boardId"), (String) parameters.get("commentId"));
    }

    public boolean updateComment(Map<String, Object> parameters) {
        return repo.updateComment((String) parameters.get("boardId"),
                new Comment((String) parameters.get("commentId"), (String) parameters.get("userId"),
                        (String) parameters.get("nickName"), (String) parameters.get("comment"), new Date()));
    }
}
