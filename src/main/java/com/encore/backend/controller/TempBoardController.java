package com.encore.backend.controller;

import com.encore.backend.service.TempBoardService;
import com.encore.backend.vo.TempBoard;
import com.encore.backend.vo.TempBoardDeleteInputForm;
import com.encore.backend.vo.TempBoardInputForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tempBoard")
public class TempBoardController {

    private final TempBoardService tempBoardService;

    @GetMapping()
    public List<TempBoard> getAllTempBoard(@RequestBody TempBoardInputForm form) {

        if(form.getTempBoardId() != null) {
            return tempBoardService.findTempBoard(form.getTempBoardId());
        }
        else if(form.getUserId() != null) {
            if(form.getPageNumber() == null) throw new IllegalArgumentException();

            return tempBoardService.findAllByUser_id(form.getUserId(), form.getPageNumber().intValue());
        }
        else throw new IllegalArgumentException();
    }

    @PostMapping()
    public ResponseEntity<String> upsertTempBoard(@RequestBody TempBoard tempBoard) {
        String result = tempBoardService.upsertTempBoard(tempBoard);
        if(result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTempBoard(@RequestBody TempBoardDeleteInputForm form) {
        if(form == null) throw new IllegalArgumentException();

        boolean result = tempBoardService.deleteTempBoard(form.getBoardIdx());
        if(result) {
            return new ResponseEntity<>("delete tempBoard Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("delete tempBoard Fail", HttpStatus.NO_CONTENT);
        }
    }

}