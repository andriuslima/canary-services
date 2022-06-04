import { Module } from '@nestjs/common';
import { NotifierService } from './notifier.service';

@Module({
  imports: [],
  providers: [NotifierService],
  exports: [NotifierService],
})
export class NotifierModule {}
