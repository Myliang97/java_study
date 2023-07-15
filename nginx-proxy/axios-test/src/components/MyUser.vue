<template>
    <div>
        <div>msg:{{ msg }}</div>
        <el-button type="primary" @click="onGetUser">发送请求</el-button>
    </div>
</template>
<script>
import api from '@/api/user'

export default {
    data(){
            return{  msg:""
        }
    },
    methods:{
        onGetUser(){

            api.getUser().then(res => 
                this.msg = res.data
            )
        }

    }
}
</script>