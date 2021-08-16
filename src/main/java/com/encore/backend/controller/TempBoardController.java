package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.dto.TempBoardDTO;
import com.encore.backend.dto.TempBoardInputForm;
import com.encore.backend.service.TempBoardService;
import com.encore.backend.vo.TempBoard;
import com.encore.backend.vo.TempBoardDeleteInputForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TempBoardController {

    private final TempBoardService tempBoardService;

    @GetMapping("/tempBoard")
    public ResponseEntity<List<TempBoard>> getAllTempBoard(@ModelAttribute TempBoardInputForm form) {

        if (form.getTempBoardId() != null) {
            return new ResponseEntity<>(tempBoardService.findTempBoard(form.getTempBoardId()), HttpStatus.OK);
        } else if (form.getUserEmail() != null) {
            if (form.getPageNumber() == null)
                throw new IllegalArgumentException();

            log.info("pageNumber={}", form.getPageNumber());
            return new ResponseEntity<>(
                    tempBoardService.findAllByUser_id(form.getUserEmail(), form.getPageNumber().intValue() - 1),
                    HttpStatus.OK);
        } else
            throw new IllegalArgumentException();
    }

    @GetMapping("/tempBoard/count")
    public ResponseEntity<Long> getTempBoardCount(@RequestParam Map<String, Object> parameters) {
        String userEmail = (String) parameters.get("userEmail");
        long count = tempBoardService.getBoardsCount(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @PostMapping("/tempBoard")
    public ResponseEntity<String> upsertTempBoard(@RequestPart MultipartFile titleImageFile,
            @RequestPart TempBoardDTO tempBoardDTO) {
        String result = tempBoardService.upsertTempBoard(tempBoardDTO, titleImageFile);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/tempBoard")
    public ResponseEntity<String> deleteTempBoard(@ModelAttribute TempBoardDeleteInputForm form) {
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