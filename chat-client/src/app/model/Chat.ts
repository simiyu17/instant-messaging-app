import { ChatMessage } from "./ChatMessage";

export interface Chat {
    chatId: Number;
    sender: string;
    receiver: string;
    messages: ChatMessage[];
}