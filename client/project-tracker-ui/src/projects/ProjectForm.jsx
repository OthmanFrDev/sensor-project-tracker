import React, { useEffect, useState } from 'react'
import { Button } from 'primereact/button'
import { Dropdown } from 'primereact/dropdown';
import { Calendar } from 'primereact/calendar';
import { createProject, getBudgets, getCategories } from '../resources/Api';
import { InputText } from 'primereact/inputtext';
import { formatDate } from '../resources/Utils';
import { useNavigate } from 'react-router-dom';


function ProjectForm(props) {
    const [categories, setCategories] = useState([])
    const [budgets, setBudgets] = useState([])
    const [newProject, setNewProject] = useState({
        name: "",
        categoryId: null,
        startDate: new Date(),
        endDate: new Date(),
        budgetId: null
    })
    const navigate = useNavigate();
    useEffect(() => {
        getCategories().then(res => setCategories(res.data))
        getBudgets().then(res => setBudgets(res.data))
    }, [])
    const budgetOptionTemplate = (option) => {
        return (
            <div className="flex align-items-center">
                <span>{"Amount : " + option?.amount + "€ - MansDay :" + option?.mansDay}</span>
            </div>
        );
    };
    const onChange = (e) => {
        setNewProject({
            ...newProject,
            [e.target.name]: e.target.value
        })
    }
    const onSubmitHandler = (e) => {
        e.preventDefault();
        const project = {
            ...newProject,
            startDate: formatDate(newProject.startDate),
            endDate: formatDate(newProject.endDate),
            userId: localStorage.getItem("user_id")
        }
        createProject(project).then(res => navigate("/projects"));
    }
    return (
        <div className="flex align-items-center justify-content-center">
            <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6">
                <form onSubmit={onSubmitHandler}>
                    <div className="text-center mb-5">
                        <div className="text-900 text-3xl font-medium mb-3">Créer un projet</div>
                    </div>
                    <div>
                        <label htmlFor="name" className="block text-900 font-medium mb-2">Nom du projet</label>
                        <InputText id="name" name="name" value={newProject.name} onChange={(e) => onChange(e)} type="text" placeholder="Project Name" className="w-full mb-3" />

                        <label htmlFor="category" className="block text-900 font-medium mb-2">Categorie</label>
                        <Dropdown
                            value={newProject.categoryId}
                            options={categories}
                            name="categoryId"
                            style={{ width: '100%' }}
                            onChange={(e) => onChange(e)}
                            optionLabel='label'
                            optionValue='id'
                            placeholder="Selectionner une categorie"
                        />

                        <label htmlFor="startDate" className="block text-900 font-medium mb-2">Date de début</label>
                        <Calendar
                            value={newProject.startDate}
                            onChange={(e) => onChange(e)}
                            dateFormat="yy-mm-dd"
                            name="startDate"
                            id="startDate"
                            maxDate={newProject.endDate}
                            showIcon
                            style={{ width: '100%' }}
                        />

                        <label htmlFor="endDate" className="block text-900 font-medium mb-2">Date de fin</label>
                        <Calendar
                            value={newProject.endDate}
                            onChange={(e) => onChange(e)}
                            dateFormat="yy-mm-dd"
                            name="endDate"
                            minDate={newProject.startDate}
                            id="endDate"
                            showIcon
                            style={{ width: '100%' }}
                        />

                        <label htmlFor="budget" className="block text-900 font-medium mb-2">Budget</label>
                        <Dropdown
                            value={newProject.budgetId}
                            options={budgets}
                            onChange={(e) => onChange(e)}
                            itemTemplate={budgetOptionTemplate}
                            optionLabel='amount'
                            optionValue='id'
                            placeholder="Selectionner un budget"
                            id="budget"
                            name="budgetId"
                            style={{ width: '100%' }}
                        />
                        <Button label="Create project" icon="pi pi-save" className="w-full mt-5" />
                    </div>
                </form>
            </div>
        </div>
    )
}


export default ProjectForm
