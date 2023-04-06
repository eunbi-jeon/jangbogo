import React from "react";
import "../../css/messagelist.css";

const MessageList = ({ messages}) => {
    return(
        <div>
            {messages.map(message => {
                return (<div key={message.id}>
                    {message.title}
                    </div>)
            })}
        </div>
    )
} 
export default MessageList;