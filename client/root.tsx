import React from "react";
import { Link, Outlet, useLoaderData } from "react-router-dom";
import { api } from "./fetcher";

type EncounterList = Array<{
    index: number,
    name: string,
    type: "TRASH" | "BOSS",
}>

export const rootLoader = api<EncounterList>(p => "/api/encounters")

export const RootPage = () => {
    const data = useLoaderData() as EncounterList;

    const items = data.filter(d => d.type === "BOSS").map(d => <li key={d.index}><Link to={`encounter/${d.index}`}>{d.name}</Link></li>)
    return <div>
        <h1>Encounters</h1>
        <ul>
            {items}
        </ul>
        <Outlet />
    </div>
}
