package com.example.david.donationtracker;

import org.junit.Test;

public class SpencerUnitTest {

    @Test
    public void validateUsernameIsCorrect() {
        String[] validUsernames = {"user@example.com", "a@a.org", "ASDFGHJKL@pEIKOrewq.gov",
                "q2w3e4r5t6y@u8hji9.sixlet", "+spencer_%_sho.ok+@place-name.EDU",
                "tricky_But.True7+7@\u006e\u0069\u0063\u0065.\u0063\u006f\u006d"};

        String[] invalidUsernames = {"username", "", "steve@jobs", "bill@gates.m",
                "~username@example.com", "`fancy@feast.meow", "!name@place.ext",
                "user@@example.sigh", "(sneaky)@place.com", "tooLong@asdfasdf.toooLong",
                "\"System.out.println(\"hacks\");\"@hacks.com",
                "tricky_and_False7+7@\u006e\u0069\u0063\u005d.\u0063\u006f\u006d"};

        // "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"

        for (String s : validUsernames) {
            System.out.println(s);
            assert(Credentials.isValidUsername(s));
        }

        for (String s : invalidUsernames) {
            System.out.println(s);
            assert(!Credentials.isValidUsername(s));
        }
    }
}
