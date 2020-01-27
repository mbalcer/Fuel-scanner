import { TestBed } from '@angular/core/testing';

import { OcrService } from './file-upload.service';

describe('FileUploadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OcrService = TestBed.get(OcrService);
    expect(service).toBeTruthy();
  });
});
