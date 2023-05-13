export interface ChatMessage {
    id: number;
    chat_id: number;
    receiver: string;
    sender: string;
    content: string;
    dateCreated: string;
}