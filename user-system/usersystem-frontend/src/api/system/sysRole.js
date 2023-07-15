import request from '@/utils/request'

const apiUrl = "/admin/system/sysRole"

export default {
    //分页数据
    getSysRolePage(page, limit, searchCodition) {
        return request({
            url: `${apiUrl}/${page}/${limit}`,
            method: 'get',
            params: searchCodition
        })
    },
    //根据id获取
    getSysRoleById(id) {
        return request({
            url: `${apiUrl}/${id}`,
            method: 'get'
        })
    },
    //获取所有角色
    getSysRoleList() {
        return request({
            url: `${apiUrl}/findAll`,
            method: 'get'
        })
    },
    //添加角色
    addSysRole(sysRole) {
        return request({
            url: `${apiUrl}/add`,
            method: 'post',
            data: sysRole
        })
    },
    //更新角色
    updateSysRole(sysRole) {
        return request({
            url: `${apiUrl}/update`,
            method: 'post',
            data: sysRole
        })
    },
    //移除id指定的角色
    removeSysRoleById(id) {
        return request({
            url: `${apiUrl}/remove/${id}`,
            method: 'delete'
        })
    },
    batchRemoveSysRole(idList) {
        return request({
            url: `${apiUrl}/batchRemove`,
            method: 'delete',
            data: idList
        })
    }

}