package com.jangbogo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Member;
import com.jangbogo.domain.Message;
import com.jangbogo.dto.MessageDTO;
import com.jangbogo.repository.MemberMSRepository;
import com.jangbogo.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final MemberMSRepository memberRepository;

	/* 쪽지쓰기 -> 저장 */
	@Transactional
	public MessageDTO write(MessageDTO messageDTO) {

		Member receiver = memberRepository.findByNickName(messageDTO.getReceiverName());
		Member sender = memberRepository.findByNickName(messageDTO.getSenderName());

		Message message = new Message();
		message.setReceiver(receiver);
		message.setSender(sender);

		message.setTitle(messageDTO.getTitle());
		message.setContent(messageDTO.getContent());
		message.setDeletedByReceiver(false);
		message.setDeletedBySender(false);
		messageRepository.save(message);

		return MessageDTO.toDto(message);
	}

	/* 받은 쪽지함 로딩*/
	@Transactional(readOnly = true)
	public List<MessageDTO> receivedMessage(Member member) {
		List<Message> messages = messageRepository.findAllByReceiver(member);
		List<MessageDTO> messageDTOs = new ArrayList<>();
		
		for (Message message : messages) {
			/*받은 쪽지함에서 쪽지를 삭제하지 않았다면 추가하여 로딩*/
			if (!message.isDeletedByReceiver()) {
				messageDTOs.add(MessageDTO.toDto(message));
			}
		}
		return messageDTOs;
	}

	/* 받은 쪽지 삭제 
	 *  orElseThrow(() : 값이 있으면 반환, 없으면 메소드로 부정한 인수를 넘겨받았다는 예외발생*/
	@Transactional
	public Object deleteMessageByReceiver(Long id, Member member) {
		Message message = messageRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("쪽지를 찾을 수 없습니다.");
		});
			/*보낸 사람 = 찾는 사람*/
		if (member == message.getSender()) {
			message.deleteByReceiver(); // 받은쪽지함에서 쪽지 삭제
			
			/* 받은 사람 , 보낸 사람 모두 쪽지를 삭제 했으면 DB에서 삭제 */
			if (message.isDeleted()) {
				messageRepository.delete(message);
				return "양쪽 모두 쪽지 삭제";
			}
			return " 받은 쪽지 삭제";
		} else {
			return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
		}
	}

	/* 보낸 쪽지함 로딩*/
	@Transactional(readOnly = true)
	public List<MessageDTO> sentMessage(Member member){
		List<Message> messages = messageRepository.findAllBySender(member);
		List<MessageDTO> messageDTOs = new ArrayList<>();
		
		for(Message message : messages) {
			if(!message.isDeletedBySender()) {
				messageDTOs.add(MessageDTO.toDto(message));
			}
		}
		return messageDTOs;
	}
	
	/*보낸 쪽지 삭제*/
	@Transactional	//message id
	public Object deleteMessageBySender(Long id,Member member) {
		Message message = messageRepository.findById(id).orElseThrow(() ->{
			return new IllegalArgumentException("쪽지를 찾을 수 없습니다.");
		});
			/*받는 사람 == 찾는 사람*/
		if(member == message.getReceiver()) {
			message.deleteBySender(); /*보낸 쪽지함에서 쪽지 삭제*/
			
			if(message.isDeleted()) {
			/*받은 사람, 보낸 사람 모두 쪽지를 삭제했으면 DB에서 삭제 */
				messageRepository.delete(message);
				return "양쪽 모두 쪽지 삭제";
			}
			return "보낸 쪽지 삭제";
		}else {
			return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
		}
	}
}