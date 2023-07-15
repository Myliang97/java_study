<template>
  <div class="app-container">
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关 键 字">
              <el-input style="width: 95%" v-model="searchCodition.keyword" placeholder="用户名/姓名/手机号码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="createTimes"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 10px;width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini"  @click="fecthSysUserList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>

     <!-- 工具条 -->
        <div class="tools-div">
            <el-button type="success" icon="el-icon-plus" size="mini" @click="addSysUser">添 加</el-button>
            <el-button class="btn-add" size="mini" @click="batchRemove" >批量删除</el-button>
        </div>
	<!-- 列表 -->
    <el-table
      v-loading="loading"
      :data="userList"
      stripe
      border
      style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange" >

    <el-table-column type="selection"/>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" width="180"/>
      <el-table-column prop="name" label="姓名" width="110"/>
      <el-table-column prop="phone" label="手机" />
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status===1"
            @change="switchStatus(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />

      <el-table-column label="操作"  align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="editById(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.id)" title="删除" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="block">
        <el-pagination
        :current-page="page"
        :total="total"
        :page-size="limit"
        layout="total, prev, pager, next, jumper"
        @current-change="handleCurrentChange"
        />
    </div>

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
      <el-form ref="dataForm" :model="sysUser"  label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username"/>
        </el-form-item>
        <el-form-item v-if="!sysUser.id" label="密码" prop="password">
          <el-input v-model="sysUser.password" type="password"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sysUser.name"/>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="addOrUpdate" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import api from "@/api/system/sysUser"
export default {
    data(){
        return{
            loading:false,
            userList:[],
            total:0,
            page:1,
            limit:5,
            searchCodition:{},
            dialogVisible: false,//弹出框
            sysUser:{},
            selectValue: [], //批量删除选择
            createTimes: [],
        }
    },
    created(){
        this.fecthSysUserList()
    },
    methods:{
        handleCurrentChange(page){
            this.page = page;
            this.fecthSysUserList()
        },
        fecthSysUserList(){
            if(this.createTimes && this.createTimes.length==2) {
                this.searchCodition.createTimeBegin = this.createTimes[0]
                this.searchCodition.createTimeEnd = this.createTimes[1]
            }
            api.getSysUserPage(this.page,this.limit,this.searchCodition)
                .then(response => {
                this.userList = response.data.records
                this.total = response.data.total
            })
        },
        handleSelectionChange(selection){
            this.selectValue = selection
        },
        //更改用户状态
        switchStatus(row) {
            //判断，如果当前用户可用，修改禁用
            row.status = row.status === 1 ? 0 : 1
            api.updateStatus(row.id,row.status)
                .then(response => {
                this.$message.success(response.message || '操作成功')
                this.fecthSysRoleList()
                })
        },
        addOrUpdate(){
            if(!this.sysUser.id) {
                this.addUser()
            } else {
                this.updateUser()
            }
        },
        addUser(){
            api.addSysUser(this.sysUser)
                .then(response => {
                //提示
                this.$message.success('操作成功')
                //关闭弹框
                this.dialogVisible = false
                //刷新
                this.fecthSysUserList()
                })           
        },
        updateUser(){
            api.updateSysUser(this.sysUser)
                .then(response => {
                //提示
                this.$message.success('操作成功')
                //关闭弹框
                this.dialogVisible = false
                //刷新
                this.fecthSysUserList()
                })     
        },
        editById(id){
            //弹出框
            this.dialogVisible = true
            //调用接口查询
            api.getSysUserById(id)
                .then(response => {
                this.sysUser = response.data
                })
        },
        removeById(id){
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
            //调用方法删除
            api.removeSysUserById(id)
                .then(response => {
                //提示
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                //刷新
                this.fecthSysUserList()
            })
            })
        },
        addSysUser(){
            this.dialogVisible = true;
            this.sysUser = {}
        },
        batchRemove(){
            //判断
            if(this.selectValue.length==0) {
                this.$message.warning('请选择要删除的记录！')
                return
            }
            this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                //数组
                var idList = []
                //获取多个复选框对应id，封装到数组里面
                // [1,2,3]
                for(var i=0;i<this.selectValue.length;i++) {
                    var obj = this.selectValue[i]
                    //id值
                    var id = obj.id
                    //放到数组里面
                    idList.push(id)
                }
                api.batchRemoveUser(idList).then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //刷新
                    this.fecthSysUserList();
                })
            })
        },
        //重置
        resetData() {
            //清空表单
            this.searchCodition = {}
            this.createTimes=[]
            //查询所有数据
            this.fecthSysRoleList()
        }
    }
}
</script>
<style>
.block{
    margin-top: 20px;
    height: 40px;
    width: 100%;
}
</style>