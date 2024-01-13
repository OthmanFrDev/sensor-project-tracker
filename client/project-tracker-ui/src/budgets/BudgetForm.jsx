import { Button } from 'primereact/button'
import { Dropdown } from 'primereact/dropdown';
import React, { useEffect, useState } from 'react'
import { getNoAssignedProjects, saveBudget } from '../resources/Api'
import { useNavigate } from 'react-router-dom';
import { InputNumber } from 'primereact/inputnumber';

function BudgetForm(props) {
    const navigate = useNavigate();
    const [projects, setProjects] = useState([]);
    const [isValidInput,setIsValidInput]=useState({
        amount:true,
        mansDay:true
    });
    const [budget, setNewBudget] = useState({
        amount: 0,
        mansDay: 0,
        projectId: null
    });
    useEffect(() => {
        getProjects()
        console.log(isValidInput?.amount)
    }, [])
    function getProjects() {
        getNoAssignedProjects().then(res => setProjects(res.data))
    }
    const onChange = (e) => {
        setNewBudget({
            ...budget,
            [e.target.name]: e.target.value
        })
        if(e?.value===0){
            setIsValidInput({...isValidInput,
                [e.target.name]:false
            })
        }
    }
    const onSubmitHandler = (e) => {
        e.preventDefault()
        saveBudget(budget).then(res=>navigate("/budgets"))
    }
    const onClickBack = (e) => {
        e.preventDefault()
        navigate("/budgets")
    }
    return (
        <div className="flex align-items-center justify-content-center">
            <div className="surface-card p-4 shadow-2 border-round w-full lg:w-6">
                <form onSubmit={onSubmitHandler}>
                    <div className="text-center mb-5">
                        <div className="text-900 text-3xl font-medium mb-3">Créer un budget</div>
                    </div>
                    <div>
                        <label htmlFor="amount" className="block text-900 font-medium mb-2">Montant</label>
                        <InputNumber
                            id="amount"
                            name="amount"
                            value={budget.amount}
                            onValueChange={onChange}
                            min={0}
                            mode="currency"
                            currency="EUR"
                            placeholder="Budget Amount"
                            className={!isValidInput?.amount ? "p-invalid w-full mb-3":"w-full mb-3"}  />

                        <label htmlFor="mansDay" className="block text-900 font-medium mb-2">Jour/Homme</label>
                        <InputNumber id="mansDay"
                            name="mansDay"
                            value={budget.mansDay}
                            onValueChange={onChange}
                            min={0}
                            mode="decimal"
                            placeholder="Mans Day"
                            useGrouping={false}
                            className={!isValidInput?.mansDay ? "p-invalid w-full mb-3":"w-full mb-3"}  />


                        <label htmlFor="project" className="block text-900 font-medium mb-2">Projet</label>
                        <Dropdown
                            value={budget.projectId}
                            options={projects}
                            onChange={onChange}
                            optionLabel='name'
                            optionValue='id'
                            placeholder="Selectionner un projet"
                            className="w-full mb-3"
                            name="projectId"
                        />

                        <Button label="créer projet" disabled={budget?.amount===0 || budget?.mansDay===0} icon="pi pi-save" className="w-full mt-5" />
                    </div>
                </form>
            </div>
        </div>
    )
}


export default BudgetForm
