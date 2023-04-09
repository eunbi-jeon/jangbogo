package com.jangbogo.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jangbogo.domain.DirectMessage;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.exeption.MemberNotEqualsException;
import com.jangbogo.exeption.MemberNotFoundException;
import com.jangbogo.exeption.MessageNotFoundException;
import com.jangbogo.payload.request.message.MessageCreateRequest;
import com.jangbogo.payload.request.message.MessageDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MessageDto createMessage(Member sender, MessageCreateRequest req) {
        Member receiver = getReceiver(req);
        LocalDateTime createAt = LocalDateTime.now();
        DirectMessage message = getMessage(sender, req, receiver , createAt);
        return MessageDto.toDto(messageRepository.save(message));
    }

    private Member getReceiver(MessageCreateRequest req) {
        return memberRepository.findByName(req.getReceiverName())
                .orElseThrow(MemberNotFoundException::new);
    }

    private DirectMessage getMessage(Member sender, MessageCreateRequest req, Member receiver ,LocalDateTime createAt) {
        return new DirectMessage(req.getTitle(), req.getContent(), sender, receiver,createAt);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> receiveMessages(Member member) {
        List<DirectMessage> messageList = messageRepository.findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(member);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(DirectMessage message : messageList) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if(!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }


    @Transactional(readOnly = true)
    public MessageDto receiveMessage(Long id, Member member) throws MemberNotEqualsException {
    	log.info("=======================receiveMEssage 실행완료=========================");
        DirectMessage message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        validateReceiveMessage(member, message);
        return MessageDto.toDto(message);
    }

    private void validateReceiveMessage(Member member, DirectMessage message) throws MemberNotEqualsException {
    	System.out.println("=======message.getReceiver()============="+message.getReceiver());
    	System.out.println("==========member========="+member);
    	log.info("=======================validate 실행완료=========================");
        if (message.getReceiver() != member) {
            throw new MemberNotEqualsException();
        }
        if (message.isDeletedByReceiver()) {
            throw new MessageNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public List<MessageDto> sendMessages(Member member) {
        List<DirectMessage> messageList = messageRepository.findAllBySenderAndDeletedBySenderFalseOrderByIdDesc(member);
        return messageList.stream()
                .map(message -> MessageDto.toDto(message))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MessageDto sendMessage(Long id, Member member) throws MemberNotEqualsException {
        DirectMessage message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        validateSendMessage(member, message);
        return MessageDto.toDto(message);
    }

    private void validateSendMessage(Member member, DirectMessage message) throws MemberNotEqualsException {
        if (message.getSender() != member) {
            throw new MemberNotEqualsException();
        }
        if (message.isDeletedByReceiver()) {
            throw new MessageNotFoundException();
        }
    }

    @Transactional
    public void deleteMessageByReceiver(Long id, Member member) throws MemberNotEqualsException {
        DirectMessage message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        processDeleteReceiverMessage(member, message);
        checkIsMessageDeletedBySenderAndReceiver(message);
    }

    private void processDeleteReceiverMessage(Member member, DirectMessage message) throws MemberNotEqualsException {
        if (message.getReceiver().equals(member)) {
            message.deleteByReceiver();
            return;
        }
        throw new MemberNotEqualsException();
    }

    private void checkIsMessageDeletedBySenderAndReceiver(DirectMessage message) {
        if (message.isDeletedByReceiver() == true && message.isDeletedBySender() == true) {
            messageRepository.delete(message);
        }
    }

    @Transactional
    public void deleteMessageBySender(Long id, Member member) throws MemberNotEqualsException {
        DirectMessage message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        processDeleteSenderMessage(member, message);
        checkIsMessageDeletedBySenderAndReceiver(message);
    }

    private void processDeleteSenderMessage(Member member, DirectMessage message) throws MemberNotEqualsException {
        if (message.getSender().equals(member)) {
            message.deleteBySender();
            return;
        }
        throw new MemberNotEqualsException();
    }
}