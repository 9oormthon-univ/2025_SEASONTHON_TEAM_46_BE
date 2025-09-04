package jpabasic.newsthinkybe.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import jpabasic.newsthinkybe.auth.security.CustomUserDetails;
import jpabasic.newsthinkybe.comment.domain.dto.CommentRequestDto;
import jpabasic.newsthinkybe.comment.domain.dto.CommentResponseDto;
import jpabasic.newsthinkybe.comment.service.CommentService;
import jpabasic.newsthinkybe.user.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    @Operation(summary="댓글 작성")
    public ResponseEntity<CommentResponseDto> writeComment(@AuthenticationPrincipal CustomUserDetails user,
                                          @RequestBody CommentRequestDto request){
        User me=user.getUser();
        CommentResponseDto dto=commentService.writeComment(me,request);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/comments")
    @Operation(summary="댓글 목록 조회")
    public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long newsId){
        List<CommentResponseDto> result=commentService.getCommentList(newsId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/comment")
    @Operation(summary="댓글 삭제")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었어요.");
    }

    @PutMapping("/comment")
    @Operation(summary="댓글 수정")
    public ResponseEntity<CommentResponseDto> editComment(@RequestParam Long commentId,
                                                          @RequestBody CommentRequestDto request){
        CommentResponseDto result=commentService.editComment(commentId,request);
        return ResponseEntity.ok(result);
    }
}
