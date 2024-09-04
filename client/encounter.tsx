import React from "react";
import { Link, Outlet, useLoaderData } from "react-router-dom";
import { api } from "./fetcher";


type Unit = {
    id: string,
    name: string,
    friendly: boolean
}

type Spell = {
    id: string,
    name: string,
}

export type Encounter = {
    name: string,
    units: Record<string, Unit>,
    spells: Record<string, Spell>,
    damageDone: Record<string, number>,
}

export const encounterLoader = api<Encounter>(p => `/api/encounters/${p.index}`)

export const Encounter = () => {
    const data = useLoaderData() as Encounter;

    const lines = Object.entries(data.damageDone)
    .filter(([unitId, damage]) => data.units[unitId].friendly)
    .sort((a, b)=> b[1] - a[1]).map(([unitId, damage]) => {
        return <tr><td><Link to={unitId}>{data.units[unitId].name}</Link></td><td>{damage}</td></tr>
    })

    return <div>
        <h1>{data.name}</h1>
        <table>
            <thead>
                <tr>
                    <th>Unit</th>
                    <th>Damage</th>
                </tr>
            </thead>
            <tbody>
                {lines}
            </tbody>
        </table>
        <Outlet />
    </div>
}
