import { Controller } from '@nestjs/common';
import { EventPattern } from '@nestjs/microservices';
import { EventService } from './event.service';

@Controller()
export class EventController {
  constructor(private readonly service: EventService) {}

  @EventPattern('CanaryEventTopic')
  handleEvent(data: any) {
    this.service.handleEvent(data.value);
  }
}
