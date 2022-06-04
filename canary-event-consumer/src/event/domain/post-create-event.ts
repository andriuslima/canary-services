import { IntegrationEvent } from './integration-event';

export interface CreatePostEvent extends IntegrationEvent {
  content: string;
}
