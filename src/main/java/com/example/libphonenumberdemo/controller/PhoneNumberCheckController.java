package com.example.libphonenumberdemo.controller;

import com.example.libphonenumberdemo.dto.PhoneNumberCheckResultDTO;
import com.example.libphonenumberdemo.dto.PhoneNumberDTO;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("check")
public class PhoneNumberCheckController {

  @Operation(summary = "Phone number check")
  @GetMapping(
      value = "/number",
      produces = { "application/json" }
  )
  public PhoneNumberCheckResultDTO check(PhoneNumberDTO phoneNumber,
                                         @ApiParam(value = "2 letter ISO 3166-1 alpha-2 country code, used only for numbers WITHOUT international prefix, for numbers with international prefix ignored. If not specified, 'US' would be used by default.",
                                             defaultValue = "US") @RequestParam(required = false, defaultValue = "US") String defaultRegion) {
    String origNumberString = phoneNumber.getPhoneNumber();
    var result = new PhoneNumberCheckResultDTO();

    PhoneNumber libPhoneNumber;
    try {
      libPhoneNumber = PhoneNumberUtil.getInstance().parse(origNumberString, defaultRegion);
    } catch (NumberParseException e) {
      result.setParsingError(e.getErrorType().toString() + " - " + e.getMessage());
      return  result;
    }

    result.setOriginalNumber(origNumberString);
    result.setValidNumber(PhoneNumberUtil.getInstance().isValidNumber(libPhoneNumber));
    result.setIsPossibleNumberWithReason(PhoneNumberUtil.getInstance().isPossibleNumberWithReason(libPhoneNumber));
    result.setPossibleNumber(PhoneNumberUtil.getInstance().isPossibleNumber(libPhoneNumber));
    if (result.isValidNumber() && result.isPossibleNumber()) {
      result.setFormatInternational(PhoneNumberUtil.getInstance().format(libPhoneNumber, PhoneNumberFormat.INTERNATIONAL));
      result.setFormatNational(PhoneNumberUtil.getInstance().format(libPhoneNumber, PhoneNumberFormat.NATIONAL));
      result.setFormatNumberForMobileDialing(PhoneNumberUtil.getInstance().formatNumberForMobileDialing(libPhoneNumber, "US", false));
    }

    return result;
  }
}
