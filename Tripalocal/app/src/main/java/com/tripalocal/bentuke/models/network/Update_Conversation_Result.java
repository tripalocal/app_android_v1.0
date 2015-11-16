package com.tripalocal.bentuke.models.network;

/**
 * Created by Frank on 12/08/2015.
 */
public class Update_Conversation_Result {
    public Update_Conversation_Result(int local_id, int global_id) {
        this.local_id = local_id;
        this.global_id = global_id;
    }

    public int getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(int global_id) {
        this.global_id = global_id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int global_id,local_id;
}
