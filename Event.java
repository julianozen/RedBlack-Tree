//
// EVENT.JAVA
//
// Describes a historical event via two public fields:
//  year        -- the year of the event (an integer)
//  description -- the text for the event (a String)
//
// The description of an event can be parsed into an array of keyword
// strings using the toKeywords() method.  Keywords are contiguous
// substrings of characters from the set [0-9, a-z, '-'] in the 
// description (note that the whole string is converted to lower case).
// Note that we make no attempt to remove duplicate keywords
// from the output array.  If a description contains no keywords,
// toKeywords() returns null.
//

import java.util.*;

class Event {

	public int year;             // the year of the event
	public String description;   // the event description

	// constructor
	public Event(int iyear, String idescription)
	{
		year = iyear;
		description = idescription;
	}

	// print method
	public String toString()
	{
		return String.valueOf(year) + " " + description;
	}

	// convert description to keyword array
	public String [] toKeywords()
	{
		//
		// Convert description string to lower-case and remove all 
		// characters other than alphanumerics, dashes, and whitespace
		//

		char [] srcchars = description.toLowerCase().toCharArray();
		char [] dstchars = new char [srcchars.length];

		int dstsize = 0;
		for (int i = 0; i < srcchars.length; i++)
		{
			char c = srcchars[i];
			if (Character.isLetterOrDigit(c) ||
					c == '-' ||
					Character.isWhitespace(c))
				dstchars[dstsize++] = c;
		}

		String dst = new String(dstchars, 0, dstsize);

		//
		// Convert simplified string into an array of tokens
		//

		ArrayList<String> tokens = new ArrayList<String>();
		Tokenizer t = new Tokenizer(dst);

		while (true)
		{
			String word = t.nextToken();
			if (word.length() == 0) // empty string means EOS
				break;
			else
				tokens.add(word);
		}

		String [] keywords = new String [tokens.size()];
		return tokens.toArray(keywords);
	}
}
