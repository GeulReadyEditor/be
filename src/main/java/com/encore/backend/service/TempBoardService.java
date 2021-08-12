package com.encore.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.encore.backend.dto.TempBoardDTO;
import com.encore.backend.repository.tempboard.TempBoardRepository;
import com.encore.backend.s3.S3Uploader;
import com.encore.backend.vo.TempBoard;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TempBoardService {

    private TempBoardRepository tempBoardRepository;
    private final S3Uploader s3Uploader;

    @Autowired
    public TempBoardService(TempBoardRepository tempBoardRepository, S3Uploader s3Uploader) {
        this.tempBoardRepository = tempBoardRepository;
        this.s3Uploader = s3Uploader;
    }

    public List<TempBoard> findAllByUser_id(String userEmail, int pageNumber) {
        return tempBoardRepository.findByUserEmail(userEmail, PageRequest.of(pageNumber, 5)).getContent();
    }

    public List<TempBoard> findTempBoard(String id) {
        List<TempBoard> list = new ArrayList<>();
        TempBoard tempBoard = tempBoardRepository.findById(id).orElse(null);
        list.add(tempBoard);

        return list;
    }

    public String upsertTempBoard(TempBoardDTO tempBoardDTO, MultipartFile titleImageFile) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TempBoard tempBoard = mapper.map(tempBoardDTO, TempBoard.class);
        try {
            if (tempBoard.getId() == null) {
                TempBoard ret = tempBoardRepository.insert(tempBoard);
                if (titleImageFile.getOriginalFilename().length() == 0) {
                    tempBoard.setTitleImage("");
                } else {
                    tempBoard.setTitleImage(s3Uploader.upload(titleImageFile, "titleImages", ret.getId()));
                }
                ret = tempBoardRepository.save(tempBoard);
                return ret.getId();
            } else {
                boolean check = tempBoardRepository.existsById(tempBoard.getId());
                if (!check)
                    return null;

                tempBoardRepository.save(tempBoard);
                return tempBoard.getId();
            }
        } catch (Exception e) {
            log.info("error ", e);
            return null;
        }
    }

    public boolean deleteTempBoard(String boardIdx) {
        try {
            boolean check = tempBoardRepository.existsById(boardIdx);
            log.info("check={}", check);
            if (!check)
                return false;

            tempBoardRepository.deleteById(boardIdx);

            return !tempBoardRepository.existsById(boardIdx);
        } catch (Exception e) {
            log.info("error ", e);
            return false;
        }
    }

    public long getBoardsCount(String userEmail) {
        long result = tempBoardRepository.countByUserEmail(userEmail);
        return result;
    }

}
