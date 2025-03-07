package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.dto.QnADTO;
import com.goingupdragon.going_up_dragon.entity.QnA;
import com.goingupdragon.going_up_dragon.repository.LikeTableRepository;
import com.goingupdragon.going_up_dragon.repository.QnARepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnaRepository;
    private final LikeTableRepository likeTableRepository;

    /**
     * QnA 엔티티를 QnADTO로 변환하는 공통 메서드
     */
    private QnADTO convertToQnADTO(QnA qna) {
        int qnaId = qna.getQnaId();

        return new QnADTO(
                qnaId,
                qna.getUser().getInfoId(),
                qna.getUser().getNickname(),
                qnaRepository.countQnAsByStudentId(qna.getUser().getInfoId()), // ✅ 학생이 질문한 개수
                qna.getCourse().getInstructor().getInfoId(),
                qna.getCourse().getInstructor().getNickname(),
                qna.getCourse().getCourseId(),
                qna.getCourse().getCourseTitle(),
                qna.getCourse().getShortDescription(),
                qna.getTitle(),
                qna.getContent(),
                qna.getCustomTag1(),
                qna.getCustomTag2(),
                qna.getCustomTag3(),
                qna.getCreatedAt(),
                qna.getViewCount(),
                likeTableRepository.countLikesByQnAId(qnaId), // ✅ 해당 질문의 좋아요 개수
                qnaRepository.countRepliesByQnAId(qnaId) // ✅ 해당 질문의 답변 개수
        );
    }

    /**
     * 특정 강의의 QnA 리스트 조회
     */
    public List<QnADTO> getQnAsByCourseId(Integer courseId) {
        List<QnA> qnaList = qnaRepository.findQnAsByCourseId(courseId);
        return qnaList.stream().map(this::convertToQnADTO).collect(Collectors.toList());
    }

    /**
     * 특정 QnA 조회
     */
    public QnADTO getQnADetail(Integer qnaId) {
        QnA qna = qnaRepository.findQnADetail(qnaId)
                .orElseThrow(() -> new EntityNotFoundException("QnA not found"));
        return convertToQnADTO(qna);
    }

    /**
     * 같은 MainCategory의 랜덤 QnA 5개 조회
     */
    public List<QnADTO> getRandomQnAsByMainCategory(Integer courseId) {
        List<QnA> qnaList = qnaRepository.findRandomQnAsByMainCategory(courseId);
        return qnaList.stream().map(this::convertToQnADTO).collect(Collectors.toList());
    }

    public List<QnADTO> getRepliesByQnAId(Integer qnaId) {
        List<QnA> replies = qnaRepository.findRepliesByQnAId(qnaId);

        // ✅ replies가 null이면 빈 리스트로 처리
        if (replies == null) {
            return Collections.emptyList();
        }

        return replies.stream()
                .map(this::convertToQnADTO)
                .collect(Collectors.toList());
    }

    public Integer getInstructorQnACount(Integer infoId){
        return qnaRepository.countInstructorQnAs(infoId);
    }

    public List<QnADTO> getInstructorQnAList(Integer infoId){
        List<QnA> qnaList = qnaRepository.findInstructorQnAs(infoId);

        return qnaList.stream().map(this::convertToQnADTO).collect(Collectors.toList());
    }
}
