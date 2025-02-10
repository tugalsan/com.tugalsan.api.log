package com.tugalsan.api.log.client;

import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCE_OutTyped;

public interface TGS_LogInterface {

    /*console link*/
    public void cl(CharSequence fucName, CharSequence text, CharSequence url);

    /*console lazy info*/ 
    public void ci(CharSequence funcName, TGS_FuncMTUCE_OutTyped<Object> callable);

    /*console info*/ 
    public void ci(CharSequence fucName, Object... oa);

    /*console results*/
    public void cr(CharSequence fucName, Object... oa);

    /*console throwable*/
    public void ct(CharSequence fucName, Throwable t);

    /*console error*/
    public void ce(CharSequence fucName, Object... oa);
}
