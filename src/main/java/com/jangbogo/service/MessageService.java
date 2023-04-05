package com.jangbogo.service;


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



@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MessageDto createMessage(Member sender, MessageCreateRequest req) {
        Member receiver = getReceiver(req);
        DirectMessage message = getMessage(sender, req, receiver);
        return MessageDto.toDto(messageRepository.save(message));
    }

    private Member getReceiver(MessageCreateRequest req) {
        return memberRepository.findByName(req.getReceiverNickname())
                .orElseThrow(MemberNotFoundException::new);
    }

    private DirectMessage getMessage(Member sender, MessageCreateRequest req, Member receiver) {
        return new DirectMessage(req.getTitle(), req.getContent(), sender, receiver);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> receiveMessages(Member member) {
    	Long id = 1L;
    	Member member1 = memberRepository.findById(id).orElse(null);
        List<DirectMessage> messageList = messageRepository.findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(member1);
        
        System.out.println("메세지 리스트 출력 "+messageList.size());
        return messageList.stream()
                .map(message -> MessageDto.toDto(message))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public MessageDto receiveMessage(Long id, Member member) throws MemberNotEqualsException {
        DirectMessage message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        validateReceiveMessage(member, message);
        return MessageDto.toDto(message);
    }

    private void validateReceiveMessage(Member member, DirectMessage message) throws MemberNotEqualsException {
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
        if (message.isDeletedMessage()) {
        	
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