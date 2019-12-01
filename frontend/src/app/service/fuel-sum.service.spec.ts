import {TestBed} from '@angular/core/testing';

import {FuelSumService} from './fuel-sum.service';

describe('FuelSumService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FuelSumService = TestBed.get(FuelSumService);
    expect(service).toBeTruthy();
  });
});
