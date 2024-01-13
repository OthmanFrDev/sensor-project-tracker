import axios from 'axios';

const baseUrl = 'http://localhost:8080/api/v1';

axios.interceptors.request.use(
    config => {
        if (config.url.endsWith("login")) {
            return config;
        }
        const token = localStorage.getItem('access_token');
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token;
        }
        config.headers['Content-Type'] = 'application/json';
        return config;
    },
    error => {
        Promise.reject(error)
    });

axios.interceptors.response.use((response) => { 
    return response
}, function (error) { // block to handle error case
    const originalRequest = error.config;
    if (error || error.response?.status === 403) {
        localStorage.removeItem("access_token")
        return Promise.reject(error);
    }
    return Promise.reject(error);
});
const getProjects = () => {
    return axios.get(`${baseUrl}/projects`)
}
const getNoAssignedProjects = () => {
    return axios.get(`${baseUrl}/projects/no-assigned`)
}
const createProject = (project) => {
    return axios.post(`${baseUrl}/projects`, project)
}
const updateProject = (id, newProject) => {
    return axios.put(`${baseUrl}/projects/${id}`, newProject)
}
const deleteProject = (id) => {
    return axios.delete(`${baseUrl}/projects/${id}`)
}
const getCategories = () => {
    return axios.get(`${baseUrl}/categories`)
}
const saveCategory = (category) => {
    return axios.post(`${baseUrl}/categories`, category)
}
const getBudgets = () => {
    return axios.get(`${baseUrl}/budgets`)
}
const saveBudget = (budget) => {
    return axios.post(`${baseUrl}/budgets`, budget)
}
const assignBudget = (id, project) => {
    return axios.patch(`${baseUrl}/budgets/${id}`, project)
}
const login = (payload) => {
    return axios.post(`${baseUrl}/login`, payload)
}
const getUsers = () => {
    return axios.get(`${baseUrl}/users`)
}
export {
    getProjects,
    getNoAssignedProjects,
    createProject,
    updateProject,
    deleteProject,
    getCategories,
    saveCategory,
    getBudgets,
    saveBudget,
    assignBudget,
    login,
    getUsers
}