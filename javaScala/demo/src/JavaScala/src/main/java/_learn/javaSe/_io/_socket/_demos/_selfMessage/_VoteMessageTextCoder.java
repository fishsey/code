package _learn.javaSe._io._socket._demos._selfMessage;

/**
 * Created by root on 5/21/17.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class _VoteMessageTextCoder implements _VoteMessageCoder
{
  /* 
   * Wire Format "VOTEPROTO" <"v" | "i"> [<RESPFLAG>] <CANDIDATE> [<VOTECNT>] 
   * Charset is fixed by the wire format. 
   */

    // Manifest constants for encoding
    public static final String MAGIC = "Voting";
    public static final String VOTESTR = "v";
    public static final String INQSTR = "i";
    public static final String RESPONSESTR = "R";

    public static final String CHARSETNAME = "US-ASCII";
    public static final String DELIMSTR = " ";
    public static final int MAX_WIRE_LENGTH = 2000;


    public byte[] encoder(_VoteMessage msg) throws IOException
    {
        String msgString = MAGIC + DELIMSTR + (msg.isInquiry() ? INQSTR : VOTESTR)
                + DELIMSTR + (msg.isResponse() ? RESPONSESTR + DELIMSTR : "")
                + Integer.toString(msg.getCandidateID()) + DELIMSTR
                + Long.toString(msg.getVoteCount());
        byte data[] = msgString.getBytes(CHARSETNAME);
        return data;
    }

    public _VoteMessage decoder(byte[] message) throws IOException
    {
        ByteArrayInputStream msgStream = new ByteArrayInputStream(message);
        Scanner s = new Scanner(new InputStreamReader(msgStream, CHARSETNAME));
        boolean isInquiry;
        boolean isResponse;
        int candidateID;
        long voteCount;
        String token;

        try
        {
            token = s.next();
            if (!token.equals(MAGIC))
            {
                throw new IOException("Bad magic string: " + token);
            }
            token = s.next();
            if (token.equals(VOTESTR))
            {
                isInquiry = false;
            } else if (!token.equals(INQSTR))
            {
                throw new IOException("Bad vote/inq indicator: " + token);
            } else
            {
                isInquiry = true;
            }

            token = s.next();
            if (token.equals(RESPONSESTR))
            {
                isResponse = true;
                token = s.next();
            } else
            {
                isResponse = false;
            }
            // Current token is candidateID
            // Note: isResponse now valid
            candidateID = Integer.parseInt(token);
            if (isResponse)
            {
                token = s.next();
                voteCount = Long.parseLong(token);
            } else
            {
                voteCount = 0;
            }
        } catch (IOException ioe)
        {
            throw new IOException("Parse error...");
        }
        return new _VoteMessage(isResponse, isInquiry, candidateID, voteCount);
    }
}  
