import React from "react";
import { createBrowserRouter, useLoaderData } from "react-router-dom";
import { rootLoader, RootPage } from "./root";
import { Encounter, encounterLoader } from "./encounter";
import { Unit, unitLoader } from "./unit";
import { Spell, spellLoader } from "./spell";


export const Router = createBrowserRouter([{
    path: "/",
    element: <RootPage />,
    loader: rootLoader,
    children: [
        {
            path: "encounter/:index",
            element: <Encounter />,
            loader: encounterLoader,
            id: "encounter",
            children: [{
                path: ":unitId",
                element: <Unit />,
                loader: unitLoader,
                children: [{
                    path: ":spellId",
                    element: <Spell />,
                    loader: spellLoader,
                }]
            }]
        }, ]
}])