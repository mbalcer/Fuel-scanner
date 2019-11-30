import { TestBed } from '@angular/core/testing';

import { GraphicService } from './graphic.service';

describe('GraphicService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GraphicService = TestBed.get(GraphicService);
    expect(service).toBeTruthy();
  });
});
