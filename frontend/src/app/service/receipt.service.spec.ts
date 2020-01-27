import {TestBed} from '@angular/core/testing';

import {ReceiptService} from './fuel-sum.service';

describe('FuelSumService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReceiptService = TestBed.get(ReceiptService);
    expect(service).toBeTruthy();
  });
});
