package org.tlg.bot.mem.util;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.tlg.bot.mem.exceptions.EncodedException;

public class EncodedTest {

    @Test
    public void sameShouldBeEqual() {
        
        assertEquals(new Encoded("1", "2"), new Encoded("1", "2"));
    }  
    
    @Test
    public void diffShouldBeNotEqual() {
        assertNotEquals(new Encoded("1 ", "2"), new Encoded("1", "2"));
    }  
    
    @Test
    public void correctEncoding() throws EncodedException {
        final String s1 = "first1 value";
        final String s2 = "SeCond2";
        final Encoded encoded = new Encoded(s1, s2);
        final String encString = encoded.encoded();
        assertThat(new Encoded(encString).first(), equalTo(s1));
        assertThat(new Encoded(encString).second(), equalTo(s2));
    }
    @Test
    public void incorrectEncoding(){
        final String s1 = "incorrect string";
        try {
            new Encoded(s1);
            fail("incorrect string was encoded");
        } catch (final EncodedException e) {
            //do nothing - correct
        }
    }
    @Test
    public void correctStringValuesEncodesDecodesOk() {
        final String s1 = "first1 value";
        final String s2 = "SeCond2";
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.encoded(), not(containsString(s1)));
        assertThat(encoded.encoded(), not(containsString(s2)));
    }
    
    @Test
    public void emptyFirstObjectShouldReturnEmptyFirstString() {
        final String s1 = "";
        final String s2 = "SeCond2";
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), isEmptyString());
        assertThat(encoded.second(), equalTo(s2));
    }
    
    @Test
    public void emptySecondObjectShouldReturnEmptySecondString() {
        final String s1 = "first";
        final String s2 = "";
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), equalTo(s1));
        assertThat(encoded.second(), isEmptyString());
    }
    
    @Test
    public void emptyObjectsShouldReturnEmptyStrings() {
        final String s1 = "";
        final String s2 = "";
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), isEmptyString());
        assertThat(encoded.second(), isEmptyString());
    }
    
    
    @Test
    public void nullObjectsShouldReturnEmptyStrings() {
        final String s1 = null;
        final String s2 = null;
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), isEmptyString());
        assertThat(encoded.second(), isEmptyString());
    }
    
    @Test
    public void nullFirstObjectShouldReturnEmptyFirstString() {
        final String s1 = null;
        final String s2 = "SeCond2";
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), isEmptyString());
        assertThat(encoded.second(), equalTo(s2));
    }
    
    @Test
    public void nullSecondObjectShouldReturnEmptySecondString() {
        final String s1 = "first";
        final String s2 = null;
        final Encoded encoded = new Encoded(s1, s2);
        System.out.printf("%-15s : %-15s :%-15s%n", s1, s2, encoded.encoded());
        assertThat(encoded.encoded(), not(isEmptyOrNullString()));
        assertThat(encoded.first(), equalTo(s1));
        assertThat(encoded.second(), isEmptyString());
    }
}
