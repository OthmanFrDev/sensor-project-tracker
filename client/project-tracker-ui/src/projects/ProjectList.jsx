import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { Column } from 'primereact/column';
import { DataTable } from 'primereact/datatable';
import { Dropdown } from 'primereact/dropdown';
import { Splitter, SplitterPanel } from 'primereact/splitter';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { deleteProject, getBudgets, getCategories, getProjects, getUsers, updateProject } from '../resources/Api';
import { formatDate } from '../resources/Utils';

function ProjectList(props) {
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const [projects, setProjects] = useState([]);
    const [categories, setCategories] = useState([]);
    const [budgets, setBudgets] = useState([]);
    const [users, setUsers] = useState([]);
    const [selectedProjects, setSelectedProjects] = useState([]);
    const [filters, setFilters] = useState({
        categoryLabel: undefined,
        user: undefined
    })
    const navigate = useNavigate();
    useEffect(() => {
        getAllProjects();
        getAllBudgets();
        getAllCategories();
        getAllUsers();
    }, [])
    useEffect(() => {
        if (filters.categoryLabel?.length > 0 && filters.user === undefined) {
            console.log("am here user undif")
            setProjects(projects.filter(p => p.categoryLabel === filters.categoryLabel))
        }
        else if (filters.user?.length > 0 && filters.categoryLabel === undefined) {
            console.log("am here label undif")
            setProjects(projects.filter(p => p.createdBy === filters.user))
        } else if (filters.user?.length > 0 && filters.categoryLabel?.length > 0) {
            console.log("am here label undif")

            setProjects(projects.filter(p => p.createdBy === filters.user && p.categoryLabel === filters.categoryLabel))
        } else {
            getAllProjects()
        }
    }, [filters])
    function getAllProjects() {
        getProjects()
            .then(res => {
                setProjects(res.data);
            })
            .catch(err => console.error(err));
    }
    function getAllCategories() {
        getCategories().then(res => setCategories(res.data))
    }
    function getAllBudgets() {
        getBudgets().then(res => setBudgets(res.data))
    }
    function getAllUsers() {
        getUsers().then(res => setUsers(res.data))
    }
    const onFilterChange = (e) => {
        setFilters({
            ...filters,
            [e.target.name]: e.value
        })
    }
    const renderHeader = () => {
        return (
            <div className="flex justify-content-end">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <Dropdown
                        value={filters.user}
                        options={users}
                        onChange={onFilterChange}
                        optionLabel='name'
                        optionValue='name'
                        name='user'
                        showClear
                        placeholder="Search by user"
                    />
                </span>
                <span className="p-input-icon-left ml-2">
                    <i className="pi pi-search" />
                    <Dropdown
                        value={filters.categoryLabel}
                        options={categories}
                        onChange={onFilterChange}
                        optionLabel='label'
                        optionValue='label'
                        name='categoryLabel'
                        showClear
                        placeholder="Search by category"
                    />
                </span>
            </div>
        );
    };
    const categoryEditor = (options) => {
        return (
            <Dropdown
                value={options.value}
                options={categories}
                onChange={(e) => options.editorCallback(e.value)}
                optionLabel='label'
                optionValue='id'
                placeholder="Select a category"
            />
        );
    };
    const budgetEditor = (options) => {
        return (
            <Dropdown
                value={options.value}
                options={budgets}
                onChange={(e) => options.editorCallback(e.value)}
                itemTemplate={budgetOptionTemplate}
                optionLabel='amount'
                optionValue='id'
                placeholder="Select a budget"
            />
        );
    };
    const budgetOptionTemplate = (option) => {
        return (
            <div className="flex align-items-center">
                <span>{"Amount : " + option?.amount + "€ - MansDay :" + option?.mansDay}</span>
            </div>
        );
    };
    const dateEditor = (options) => {
        return (
            <Calendar
                value={options.field === 'startDate' ? startDate : endDate}
                onChange={(e) => {
                    const value = e.target.value;
                    options.editorCallback(value);
                    options.field === 'startDate' ? setStartDate(value) : setEndDate(value)
                }}
                dateFormat="yy-mm-dd"
                minDate={options.field === 'endDate' ? startDate : endDate}
                showIcon
            />
        );
    };
    const onRowEditComplete = (e) => {
        let { newData, data } = e;
        const formattedData = {
            ...newData,
            startDate: formatDate(newData.startDate),
            endDate: formatDate(newData.endDate),
            categoryId: newData?.categoryLabel || categories.filter(c => c.categoryLabel === data.categoryLabel)?.id,
            budgetId: newData.budget ? newData.budget.id : null
        }
        console.log(formattedData)
        delete formattedData.budget
        delete formattedData.categoryLabel;
        updateProject(formattedData.id, formattedData).then(res => getAllProjects())
    };
    const deleteProjects = () => {
        selectedProjects.forEach(prj => deleteProject(prj.id).then(res => getAllProjects()));
    }
    return (
        <div className="card">
            <div className="grid align-items-center justify-content-center">
                <Splitter style={{ height: '300px' }} layout="vertical">
                    <SplitterPanel className="flex align-items-center justify-content-center">
                        <div className="flex justify-content-center align-items-center mt-2 mb-4 gap-2">
                            <Button onClick={deleteProjects} disabled={selectedProjects.length === 0}><i className="pi pi-trash" style={{ fontSize: '1.5rem' }}></i>Supprimer {selectedProjects.length > 1 ? "les projets sélectionnés" : "le projet sélectionné"}</Button>
                            <Button onClick={() => navigate("/projects/add")}><i className="pi pi-plus" style={{ fontSize: '1.5rem' }}></i> Créer un projet</Button>
                        </div>
                    </SplitterPanel>
                    <SplitterPanel className="flex align-items-center justify-content-center">
                        <DataTable
                            value={projects}
                            editMode="row"
                            dataKey="id"
                            selectionMode={'checkbox'}
                            selection={selectedProjects}
                            onSelectionChange={(e) => setSelectedProjects
                                (e.value)}
                            onRowEditComplete={onRowEditComplete}
                            header={renderHeader()}
                            tableStyle={{ minWidth: '50rem' }}>
                            <Column selectionMode="multiple" headerStyle={{ width: '3rem' }}></Column>
                            <Column field="id" header="Id" style={{ width: '20%' }}></Column>
                            <Column field="categoryLabel" header="Category" editor={(options) => categoryEditor(options)} style={{ width: '20%' }}></Column>
                            <Column field="startDate" header="start Date" editor={(options) => dateEditor(options)} style={{ width: '30%' }}></Column>
                            <Column field="endDate" header="end Date" editor={(options) => dateEditor(options)} style={{ width: '30%' }}></Column>
                            <Column field="budget.amount" header="Budget" editor={(options) => budgetEditor(options)} style={{ width: '30%' }}></Column>
                            <Column field="createdBy" header="Created by" style={{ width: '30%' }}></Column>
                            <Column rowEditor={true} headerStyle={{ width: '10%', minWidth: '8rem' }} bodyStyle={{ textAlign: 'center' }}></Column>
                        </DataTable>
                    </SplitterPanel>
                </Splitter>
            </div>
        </div>
    )
}

export default ProjectList
