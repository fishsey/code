package _learn.javaSe._io._socket._demos._selfMessage;

/**
 * Created by root on 5/21/17.
 */
public class _VoteMessage
{
    private boolean isInquiry; // true if inquiry from client; false if vote from client
    private boolean isResponse;// true if response from server
    private int candidateID;   // in [0,1000]
    private long voteCount;    // nonzero only in response, zero if request

    public static final int MAX_CANDIDATE_ID = 1000;

    public _VoteMessage(boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
            throws IllegalArgumentException
    {
        // check invariants
        if (voteCount != 0 && !isResponse)
        {
            throw new IllegalArgumentException("Request vote count must be zero");
        }
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID)
        {
            throw new IllegalArgumentException("Bad Candidate ID: " + candidateID);
        }
        if (voteCount < 0)
        {
            throw new IllegalArgumentException("Total must be >= zero");
        }
        this.candidateID = candidateID;
        this.isResponse = isResponse;
        this.isInquiry = isInquiry;
        this.voteCount = voteCount;
    }

    public void setInquiry(boolean isInquiry)
    {
        this.isInquiry = isInquiry;
    }

    public void setResponse(boolean isResponse)
    {
        this.isResponse = isResponse;
    }

    public boolean isInquiry()
    {
        return isInquiry;
    }

    public boolean isResponse()
    {
        return isResponse;
    }

    public void setCandidateID(int candidateID) throws IllegalArgumentException
    {
        if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID)
        {
            throw new IllegalArgumentException("Bad Candidate ID: " + candidateID);
        }
        this.candidateID = candidateID;
    }

    public int getCandidateID()
    {
        return candidateID;
    }

    public void setVoteCount(long count)
    {
        if ((count != 0 && !isResponse) || count < 0)
        {
            throw new IllegalArgumentException("Bad vote count");
        }
        voteCount = count;
    }

    public long getVoteCount()
    {
        return voteCount;
    }

    public String toString()
    {
        String res = (isInquiry ? "inquiry" : "vote") + " for candidate " + candidateID;
        if (isResponse)
        {
            res = "response to " + res + " who now has " + voteCount + " vote(s)";
        }
        return res;
    }
}