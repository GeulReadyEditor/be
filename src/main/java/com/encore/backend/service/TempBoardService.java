package com.encore.backend.service;

import com.encore.backend.repository.TempBoardRepository;
import com.encore.backend.vo.TempBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String upsertTempBoard(TempBoard tempBoard) {
        try {
            if(tempBoard.getId() == null) {
                TempBoard ret = tempBoardRepository.insert(tempBoard);
                return ret.getId();
            } else {
                boolean check = tempBoardRepository.existsById(tempBoard.getId());
                if(!check) return null;

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
            if(!check) return false;

            tempBoardRepository.deleteById(boardIdx);

            return !tempBoardRepository.existsById(boardIdx);
        } catch (Exception e) {
            log.info("error ", e);
            return false;
        }
    }

}
