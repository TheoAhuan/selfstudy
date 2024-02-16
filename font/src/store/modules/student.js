export default{
    state:{
        
        
        studentId:"",
        student:"",
        flagLogin:false,
        
    },
    getters:{
        getStudent(state){
            return state.student
        },
        getFlag(state){
            return state.flagLogin
        },
        getStudentId(state){
            return state.studentId
        }

    },
    mutations:{
        setStudent(state,data){
            state.student=data;
        },
        setFlag(state,data){

            state.flagLogin=data
        },
        setStudentId(state,data){
            state.studentId=data
        }
    },
    actions:{
        setStudent({ commit }, data) {
            commit('setStudent', data);
          },
        setFlag({commit},data){
            commit('setFlag',data)
        },
        setStudentId({commit},data){
            commit('setStudentId',data)
        }
    }
}