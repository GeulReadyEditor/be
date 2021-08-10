package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.service.TempBoardService;
import com.encore.backend.vo.TempBoard;
import com.encore.backend.vo.TempBoardDeleteInputForm;
import com.encore.backend.dto.TempBoardInputForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tempBoard")
public class TempBoardController {

    private final TempBoardService tempBoardService;

    @GetMapping()
    public ResponseEntity<List<TempBoard>> getAllTempBoard(@RequestBody TempBoardInputForm form) {

        if (form.getTempBoardId() != null) {
            return new ResponseEntity<>(tempBoardService.findTempBoard(form.getTempBoardId()), HttpStatus.OK);
        } else if (form.getUserEmail() != null) {
            if (form.getPageNumber() == null)
                throw new IllegalArgumentException();

            return new ResponseEntity<>(
                    tempBoardService.findAllByUser_id(form.getUserEmail(), form.getPageNumber().intValue()),
                    HttpStatus.OK);
        } else
            throw new IllegalArgumentException();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTempBoardCount(@RequestParam Map<String, Object> parameters) {
        String userEmail = (String) parameters.get("userEmail");
        long count = tempBoardService.getBoardsCount(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @PostMapping()
    public ResponseEntity<String> upsertTempBoard(@RequestBody TempBoard tempBoard) {
        String result = tempBoardService.upsertTempBoard(tempBoard);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTempBoard(@RequestBody TempBoardDeleteInputForm form) {
        if (form == null)
            throw new IllegalArgumentException();

        boolean result = tempBoardService.deleteTempBoard(form.getBoardIdx());
        if (result) {
            return new ResponseEntity<>("delete tempBoard Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("delete tempBoard Fail", HttpStatus.NO_CONTENT);
        }
    }

}