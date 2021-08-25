package com.example.libphonenumberdemo.dto;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.Data;

@Data
public class PhoneNumberCheckResultDTO {

  private String originalNumber;
  private String parsingError;
  private boolean isValidNumber;

  private String formatNational;
  private String formatInternational;
  private String formatNumberForMobileDialing;

  private PhoneNumberUtil.ValidationResult isPossibleNumberWithReason;
  private boolean isPossibleNumber;

}
