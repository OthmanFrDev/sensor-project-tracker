import { Route, Routes, useNavigate } from 'react-router-dom'
import ProjectList from './projects/ProjectList'
import Login from './login/Login'
import ProjectForm from './projects/ProjectForm'
import BudgetList from './budgets/BudgetList'
import BudgetForm from './budgets/BudgetForm'
import { Button } from 'primereact/button'
import PrivateRoutes from './resources/PrivateRoutes'


function App() {
  const navigate = useNavigate();
  return (
    <div className="surface-0">
      <div className="text-900 font-bold text-6xl mb-4 text-center">Project tracker<br />
        {
          localStorage.getItem("access_token")?.length > 0 &&
          <>
            <Button label="Home" onClick={() => navigate("/projects")} icon="pi pi-home" className="w-20 ml-2" />
            <Button label="Budgets" onClick={() => navigate("/budgets")} icon="pi pi-home" className="w-20 ml-2" />
            <Button label="Se déconnecter" onClick={() => {
              localStorage.removeItem("access_token");
              navigate("/")
            }} icon="pi pi-home" className="w-20 ml-2" />
          </>
        }

      </div>
      <div className="text-700 text-xl mb-6 text-center line-height-3">Une application qui permet la gestion et le suivi des projets informatiques</div>

      <Routes>
        <Route path='/' Component={Login} />
        <Route element={<PrivateRoutes />}>
          <Route path='/projects' Component={ProjectList} />
          <Route path='/projects/add' Component={ProjectForm} />
          <Route path='/budgets' Component={BudgetList} />
          <Route path='/budgets/add' Component={BudgetForm} />
          <Route path='*' Component={ProjectList} />
        </Route>
      </Routes>
    </div>
  )
}

export default App
