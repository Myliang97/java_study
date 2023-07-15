import request from '@/utils/request'

export default{
    getUser() {
        return request({
            url: '/user',
            method: 'get'
          })
    }
}