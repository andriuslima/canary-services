import { Test, TestingModule } from '@nestjs/testing';
import { NotifierService } from './notifier.service';

describe('NotifierService', () => {
  let service: NotifierService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      imports: [],
      providers: [NotifierService],
    }).compile();

    service = module.get<NotifierService>(NotifierService);
  });

  it('Should be defined', () => {
    expect(service).toBeDefined();
  });
});
