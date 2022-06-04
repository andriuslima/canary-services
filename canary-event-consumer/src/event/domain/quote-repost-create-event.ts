import { IntegrationEvent } from './integration-event';

export interface CreateQuoteRepostEvent extends IntegrationEvent {
  parent: number;
  content: string;
}
