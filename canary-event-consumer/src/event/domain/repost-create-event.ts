import { IntegrationEvent } from './integration-event';

export interface CreateRepostEvent extends IntegrationEvent {
  parent: number;
}
