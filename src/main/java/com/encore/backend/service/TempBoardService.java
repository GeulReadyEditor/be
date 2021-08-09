package com.encore.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.encore.backend.repository.tempboard.TempBoardRepository;
import com.encore.backend.vo.TempBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TempBoardService {

    private TempBoardRepository tempBoardRepository;

    @Autowired
    public TempBoardService(TempBoardRepository tempBoardRepository) {
        this.tempBoardRepository = tempBoardRepository;
    }

    public List<TempBoard> findAllByUser_id(String userEmail, int pageNum) {
        // Page<TempBoard> ret = tempBoardRepository.findAll(userId,
        // PageRequest.of(pageNum, 10));
        return tempBoardRepository
                .findByUserEmail(userEmail, PageRequest.of(pageNum, 10, Sort.Direction.DESC, "userEmail")).getContent();
    }

    public List<TempBoard> findTempBoard(String id) {
        List<TempBoard> list = new ArrayList<>();
        TempBoard tempBoard = tempBoardRepository.findById(id).orElse(null);
        list.add(tempBoard);

        return list;
    }

    public String upsertTempBoard(TempBoard tempBoard) {
        try {
            if (tempBoard.getId() == null) {
                TempBoard ret = tempBoardRepository.insert(tempBoard);
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
