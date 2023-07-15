import request from '@/utils/request'

const apiUrl = "/admin/system/sysUser"

export default {
    //分页数据
    getSysUserPage(page, limit, searchCodition) {
        return request({
            url: `${apiUrl}/${page}/${limit}`,
            method: 'get',
            params: searchCodition
        })
    },
    //根据id获取
    getSysUserById(id) {
        return request({
            url: `${apiUrl}/${id}`,
            method: 'get'
        })
    },
    //获取所有用户
    getSysUserList() {
        return request({
            url: `${apiUrl}/list`,
            method: 'get'
        })
    },
    //添加用户
    addSysUser(sysUser) {
        return request({
            url: `${apiUrl}/add`,
            method: 'post',
            data: sysUser
        })
    },
    //更新用户
    updateSysUser(sysUser) {
        return request({
            url: `${apiUrl}/update`,
            method: 'post',
            data: sysUser
        })
    },
    batchRemoveUser(idList) {
        return request({
            url: `${apiUrl}/batchRemove`,
            method: 'delete',
            data: idList
        })
    },
    //移除id指定的用户
    removeSysUserById(id) {
        return request({
            url: `${apiUrl}/remove/${id}`,
            method: 'delete'
        })
    },
    updateStatus(id, status) {

    }

}