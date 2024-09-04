import React from "react";
import { Link, Outlet, useLoaderData, useRouteLoaderData } from "react-router-dom";
import { api } from "./fetcher";
import { Encounter } from "./encounter";


type Unit = {
    id: string,
    name: string,
    friendly: boolean,
    owner: string,
    damageBySpell: Record<string, number>,
}


export const unitLoader = api<Unit>(p => `/api/encounters/${p.index}/${p.unitId}`)

export const Unit = () => {
    const encounterData = useRouteLoaderData("encounter") as Encounter;
    const data = useLoaderData() as Unit;

    const lines = Object.entries(data.damageBySpell)
        .sort((a, b) => b[1] - a[1])
        .map(([spellId, damage]) => {
            return <tr><td><Link to={spellId}>{encounterData.spells[spellId].name}</Link></td><td>{damage}</td></tr>
        })

    return <div>
        <table>
        <thead>
                <tr>
                    <th>Spell</th>
                    <th>Damage</th>
                </tr>
            </thead>
            <tbody>
                {lines}
            </tbody>
        </table>
    </div>
}
