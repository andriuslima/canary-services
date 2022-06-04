import { IntegrationEvent } from '../domain/integration-event';

export interface Handler<t extends IntegrationEvent> {
  handle(event: t);
}
