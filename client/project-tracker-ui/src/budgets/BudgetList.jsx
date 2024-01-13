import React, { useEffect, useState } from 'react'
import { getNoAssignedProjects, getBudgets, assignBudget } from '../resources/Api';
import { Splitter, SplitterPanel } from 'primereact/splitter';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { useNavigate } from 'react-router-dom';

function BudgetList(props) {
    const navigate = useNavigate()
    const [projects, setProjects] = useState([]);
    const [budgets, setBudgets] = useState([]);
    useEffect(() => {
        getAllBudgets()
        getProjects()
    }, [])
    function getProjects() {
        getNoAssignedProjects().then(res => setProjects(res.data))
    }
    function getAllBudgets() {
        getBudgets().then(res => setBudgets(res.data))
    }
    const projectEditor = (options) => {
        return (
            <Dropdown
                value={options.value}
                options={projects}
                onChange={(e) => options.editorCallback(e.value)}
                optionLabel='name'
                optionValue='id'
                placeholder="Select a project"
            />
        );
    };
    const onRowEditComplete = (e) => {
        let { newData, index } = e;
        assignBudget(newData.id, { projectId: newData.projectName }).then(res => getAllBudgets())
    }
    return (
        <div className="card">
            <div className="grid align-items-center justify-content-center">
                <Splitter style={{ height: '300px' }} layout="vertical">
                    <SplitterPanel className="flex align-items-center justify-content-center">
                        <div className="flex justify-content-center align-items-center mt-2 mb-4 gap-2">
                            <Button onClick={() => navigate("/budgets/add")}><i className="pi pi-plus" style={{ fontSize: '1.5rem' }}></i> Créer un Budget</Button>
                        </div>
                    </SplitterPanel>
                    <SplitterPanel className="flex align-items-center justify-content-center">
                        <DataTable
                            value={budgets}
                            editMode="row"
                            dataKey="id"
                            onRowEditComplete={onRowEditComplete}
                            tableStyle={{ minWidth: '50rem' }}>
                            <Column field="id" header="Id" style={{ width: '20%' }}></Column>
                            <Column field="amount" header="Amount" style={{ width: '20%' }}></Column>
                            <Column field="mansDay" header="Mans Day" style={{ width: '30%' }}></Column>
                            <Column field="projectName" header="Assigned to Project" editor={(options) => projectEditor(options)} style={{ width: '30%' }}></Column>
                            <Column rowEditor={true} headerStyle={{ width: '10%', minWidth: '8rem' }} bodyStyle={{ textAlign: 'center' }}></Column>
                        </DataTable>
                    </SplitterPanel>
                </Splitter>
            </div>
        </div>
    )
}


export default BudgetList
