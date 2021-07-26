package com.encore.backend.service;

import com.encore.backend.repository.TempBoardRepository;
import com.encore.backend.vo.TempBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TempBoardService {

    private TempBoardRepository tempBoardRepository;

    @Autowired
    public TempBoardService(TempBoardRepository tempBoardRepository) {
        this.tempBoardRepository = tempBoardRepository;
    }

    public List<TempBoard> findAllByUser_id(String userId, int pageNum) {
        //Page<TempBoard> ret = tempBoardRepository.findAll(userId, PageRequest.of(pageNum, 10));
        return tempBoardRepository.
                findByUserId(userId, PageRequest.of(pageNum, 10, Sort.Direction.DESC, "userId"))
                .getContent();
    }

    public List<TempBoard> findTempBoard(String id) {
        List<TempBoard> list = new ArrayList<>();
        TempBoard tempBoard = tempBoardRepository.findById(id).orElse(null);
        list.add(tempBoard);

        return list;
    }

    public boolean writeTempBoard(TempBoard tempBoard) {
        try {
            boolean exists = false;
            log.info("id={}", tempBoard.getId());
            // Exist로 존재 유무 파악! modDateTime, userId를 사용하자!
            if(tempBoard.getId() != null) {
                exists = tempBoardRepository.existsById(tempBoard.getId());
                log.info("exists? {}", exists);
            }
            if(exists) {
                tempBoardRepository.save(tempBoard);
            } else {
                tempBoardRepository.insert(tempBoard);
            }

            return true;
        } catch (Exception e) {
            log.info("error ", e);
            return false;
        }
    }

    public boolean deleteTempBoard(String boardIdx) {
        try {
            boolean check = tempBoardRepository.existsById(boardIdx);
            log.info("check={}", check);
            if(!check) return false;

            tempBoardRepository.deleteById(boardIdx);

            return !tempBoardRepository.existsById(boardIdx);
        } catch (Exception e) {
            log.info("error ", e);
            return false;
        }
    }

}
