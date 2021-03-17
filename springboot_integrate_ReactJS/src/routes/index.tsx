import * as React from "react";
import { NativeRouter, Route, Switch } from "react-router-native";

import { Registration } from "../components/auth/Registration";
import { Login } from "../components/auth/Login";


export const Routes = () => (
  <NativeRouter initialEntries={["/login"]}>
    <Switch>
      <Route exact={true} path="/user/register" component={Registration} />
      <Route exact={true} path="/login" component={Login} />
    </Switch>
  </NativeRouter>
);
