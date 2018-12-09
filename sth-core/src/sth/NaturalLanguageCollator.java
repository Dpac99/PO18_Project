package sth;

import java.text.Collator;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

public class NaturalLanguageCollator implements Comparator<String>, Serializable {

    private static final long serialVersionUID = 201810051538L;

    /**
    * Compares two String objects using Natural Language as reference.
    * 
    * @param first is the first String we want to compare.
    * @param second is the String we want to compare 'first' to.
    * @return  a negative integer, zero, or a positive integer as this person's name is 
    *          less than, equal to, or greater than the specified person's name.
    */
    public int compare(String first, String second){
        return Collator.getInstance(Locale.getDefault()).compare(first,second);
    }
}