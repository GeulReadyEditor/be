package com.encore.backend.controller;

import com.encore.backend.service.TempBoardService;
import com.encore.backend.vo.TempBoard;
import com.encore.backend.vo.TempBoardInputForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tempboard")
public class TempBoardController {

    private final TempBoardService tempBoardService;

    @GetMapping()
    public List<TempBoard> getAllTempBoard(@RequestBody TempBoardInputForm form) {

        if(form.getTempBoardId() != null) {
            return tempBoardService.findTempBoard(form.getTempBoardId());
        }
        else if(form.getUserId() != null) {
            int pageNum = form.getPageNumber().intValue();
            log.info("pageNum={}", pageNum);

            return tempBoardService.findAllByUser_id(form.getUserId(), pageNum);
        }
        return null;
    }

    @PostMapping()
    public ResponseEntity<String> writeTempBoard(@RequestBody TempBoard tempBoard) {
        boolean result = tempBoardService.writeTempBoard(tempBoard);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert tempBoard " + (result ? "success" : "fail"));
    }

    @PutMapping()
    public ResponseEntity<String> updateTempBoard(@RequestBody TempBoard tempBoard) {
        boolean result = tempBoardService.updateTempBoard(tempBoard);
        return new ResponseEntity<>("ok?", HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTempBoard(@RequestBody Map<String, Object> parameter) {
        boolean result = tempBoardService.deleteTempBoard((String)parameter.get("boardIdx"));
        log.info("result={}", result);
        if(result) {
            return new ResponseEntity<>("delete tempBoard Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("delete tempBoard Fail", HttpStatus.NO_CONTENT);
        }
    }

}