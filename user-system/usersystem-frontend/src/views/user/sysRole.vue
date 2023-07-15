<template>
    <div class="app-container">

        <!--查询表单-->
        <div class="search-div">
        <el-form label-width="70px" size="small">
            <el-row>
            <el-col :span="24">
                <el-form-item label="角色名称">
                <el-input style="width: 50%" v-model="searchCodition.roleName" placeholder="角色名称"></el-input>
                </el-form-item>
            </el-col>
            </el-row>
            <el-row style="display:flex">
            <el-button type="primary" icon="el-icon-search" size="mini"  @click="fecthSysRoleList">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
            </el-row>
        </el-form>
        </div>

            <!-- 工具条 -->
        <div class="tools-div">
            <el-button type="success" icon="el-icon-plus" size="mini" @click="addSysRole">添 加</el-button>
            <el-button class="btn-add" size="mini" @click="batchRemove" >批量删除</el-button>
        </div>

        <!-- 表格 -->
        <el-table
        v-loading="loading"
        :data="roleList"
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

        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="roleCode" label="角色编码" />
        <el-table-column prop="createTime" label="创建时间" width="160"/>
        <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="editById(scope.row.id)" title="修改"/>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.id)" title="删除"/>
            </template>
        </el-table-column>
        </el-table>


    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%" >
          <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
            <el-form-item label="角色名称">
              <el-input v-model="sysRole.roleName"/>
            </el-form-item>
            <el-form-item label="角色编码">
              <el-input v-model="sysRole.roleCode"/>
            </el-form-item>
            <el-form-item label="角色描述">
              <el-input v-model="sysRole.description"/>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
            <el-button type="primary" icon="el-icon-check" @click="addOrUpdate" size="small">确 定</el-button>
          </span>
    </el-dialog>

        <div class="block">
            <el-pagination
            background
            @current-change="handleCurrentChange"
            layout="total, prev, pager, next, jumper"
            :page-size="limit"
            :total="total">
            </el-pagination>
        </div>

    </div>
</template>
<script>
import api from "@/api/system/sysRole"
export default {
    data(){
    return {
        loading:false,
        roleList:[],
        total:0,
        page:1,
        limit:5,
        searchCodition:{},
        dialogVisible: false,//弹出框
        sysRole:{}, //封装添加表单数据
        selectValue:[] //复选框选择内容封装数组
    }
    },
    //页面渲染前调用
    created(){
        this.fecthSysRoleList();
    },
    methods:{
        //拉取角色列表
        fecthSysRoleList(){
            this.loading = true;
            api.getSysRolePage(this.page,this.limit,this.searchCodition)
            .then(
                response =>{
                    this.loading = false;
                    this.total = response.data.total
                    this.roleList = response.data.records
                }
            )

        },
        handleCurrentChange(page){
            this.page = page;
            this.fecthSysRoleList();
        },
        handleSelectionChange(selection){
            this.selectValue = selection
        },
        editById(id){
            //弹出框
            this.dialogVisible = true
            console.log(id,typeof(id))
            api.getSysRoleById(id).then(response => {
                this.sysRole = response.data
                console.log(this.sysRole)
            })
        },
        removeById(id){
            this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                //调用方法删除
                api.removeSysRoleById(id)
                    .then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //刷新
                    this.fecthSysRoleList()
                })
                })
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
                api.batchRemoveSysRole(idList).then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    //刷新
                    this.fecthSysRoleList()
                })
                })
        },

        //点击添加，弹出框
        addSysRole() {
            this.dialogVisible = true
            this.sysRole = {}
        },
        addOrUpdate(){
            //判断添加还是修改
            if(!this.sysRole.id) {
                //添加
                this.addRole()
            } else {
                this.updateRole()
            }
        },
        addRole(){
            api.addSysRole(this.sysRole)
                    .then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '添加成功!'
                    });
                    //关闭弹框
                    this.dialogVisible = false
                    //刷新页面
                    this.fecthSysRoleList()
                })
        },
        updateRole(){
            api.updateSysRole(this.sysRole)
                    .then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '修改成功!'
                    });
                    //关闭弹框
                    this.dialogVisible = false
                    //刷新页面
                    this.fecthSysRoleList()
                })
        },

        //重置
        resetData() {
            //清空表单
            this.searchCodition = {}
            //查询所有数据
            this.fecthSysRoleList()
        },

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