import { Route, Router, Switch } from 'wouter';
import { HomePage } from '../pages/HomePage';
import { AuthPage } from '../pages/AuthPage';
import {GasStationsPage} from "../pages/GasStationsPage";
import {GasStationSummaryPage} from "../pages/GasStationSummaryPage";

export const AppRoutes: React.FC = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" component={HomePage} />
                <Route path="/auth" component={AuthPage} />
                <Route path="/gas-stations" component={GasStationsPage} />
                <Route path="/gas-stations/address/summary/:id" component={GasStationSummaryPage} />
            </Switch>
        </Router>
    );
};
